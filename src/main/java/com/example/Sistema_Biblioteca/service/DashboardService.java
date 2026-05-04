package com.example.Sistema_Biblioteca.service;

import com.example.Sistema_Biblioteca.dto.response.DashboardResponse;
import com.example.Sistema_Biblioteca.dto.response.TopLibroResponse;
import com.example.Sistema_Biblioteca.dto.response.VentaDiaResponse;
import com.example.Sistema_Biblioteca.entity.DetalleVenta;
import com.example.Sistema_Biblioteca.repository.DetalleVentaRepository;
import com.example.Sistema_Biblioteca.repository.LibroRepository;
import com.example.Sistema_Biblioteca.repository.UsuarioRepository;
import com.example.Sistema_Biblioteca.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final VentaRepository ventaRepository;
    private final UsuarioRepository usuarioRepository;
    private final LibroRepository libroRepository;
    private final DetalleVentaRepository detalleVentaRepository;

    public DashboardResponse obtenerMetricas() {
        LocalDate hoy = LocalDate.now();

        LocalDateTime inicioHoy = hoy.atStartOfDay();
        LocalDateTime finHoy = hoy.atTime(LocalTime.MAX);

        YearMonth mesActual = YearMonth.now();
        LocalDateTime inicioMes = mesActual.atDay(1).atStartOfDay();
        LocalDateTime finMes = mesActual.atEndOfMonth().atTime(LocalTime.MAX);

        Long ventasHoy = ventaRepository.countByCreadoEnBetween(inicioHoy, finHoy);
        BigDecimal ingresosHoy = ventaRepository.sumarIngresosEntreFechas(inicioHoy, finHoy);
        BigDecimal ingresosMes = ventaRepository.sumarIngresosEntreFechas(inicioMes, finMes);

        Long totalUsuariosActivos = usuarioRepository.countByActivoTrue();
        Long totalLibrosActivos = libroRepository.countByActivoTrue();

        List<TopLibroResponse> top5Libros = obtenerTop5Libros();
        List<VentaDiaResponse> ventasUltimos7Dias = obtenerVentasUltimos7Dias();

        return new DashboardResponse(
                ventasHoy,
                ingresosHoy,
                ingresosMes,
                totalUsuariosActivos,
                totalLibrosActivos,
                top5Libros,
                ventasUltimos7Dias
        );
    }

    private List<TopLibroResponse> obtenerTop5Libros() {
        Map<Long, TopLibroTemp> acumulado = new HashMap<>();

        List<DetalleVenta> detalles = detalleVentaRepository.findAll();

        for (DetalleVenta detalle : detalles) {
            Long libroId = detalle.getLibro().getIdLibro();

            acumulado.putIfAbsent(
                    libroId,
                    new TopLibroTemp(
                            detalle.getLibro().getTitulo(),
                            detalle.getLibro().getAutor(),
                            0L
                    )
            );

            TopLibroTemp actual = acumulado.get(libroId);
            actual.cantidadVendida += detalle.getCantidad();
        }

        return acumulado.values()
                .stream()
                .sorted(Comparator.comparingLong(TopLibroTemp::getCantidadVendida).reversed())
                .limit(5)
                .map(item -> new TopLibroResponse(
                        item.titulo,
                        item.autor,
                        item.cantidadVendida
                ))
                .toList();
    }

    private List<VentaDiaResponse> obtenerVentasUltimos7Dias() {
        LocalDate hoy = LocalDate.now();

        return java.util.stream.IntStream.rangeClosed(0, 6)
                .mapToObj(i -> hoy.minusDays(6L - i))
                .map(fecha -> {
                    LocalDateTime inicio = fecha.atStartOfDay();
                    LocalDateTime fin = fecha.atTime(LocalTime.MAX);

                    Long totalVentas = ventaRepository.countByCreadoEnBetween(inicio, fin);
                    BigDecimal ingresos = ventaRepository.sumarIngresosEntreFechas(inicio, fin);

                    return new VentaDiaResponse(fecha, totalVentas, ingresos);
                })
                .toList();
    }

    private static class TopLibroTemp {

        private String titulo;
        private String autor;
        private Long cantidadVendida;

        public TopLibroTemp(String titulo, String autor, Long cantidadVendida) {
            this.titulo = titulo;
            this.autor = autor;
            this.cantidadVendida = cantidadVendida;
        }

        public Long getCantidadVendida() {
            return cantidadVendida;
        }
    }
}
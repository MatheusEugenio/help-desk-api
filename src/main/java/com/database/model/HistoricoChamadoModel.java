package com.database.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "historico_chamado")
public class HistoricoChamadoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 30)
    private String tipoAlteracao;

    @Column(length = 40)
    private String valorAnterior;

    @Column(length = 40)
    private String novoValor;

    @ManyToOne
    @JoinColumn(name = "chamado_id", nullable = false)
    @NotNull
    private ChamadoModel chamado;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    @NotNull
    private UsuarioModel autor;
}

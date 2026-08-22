package com.database.model;

import com.database.enums.PrioridadeEnum;
import com.database.enums.StatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "chamado")
public class ChamadoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String titulo;

    @NotBlank
    @Column(nullable = false)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false)
    private PrioridadeEnum prioridade;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false)
    private StatusEnum status;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    @NotNull
    private Categoria categoria;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "solicitante_id", nullable = false)
    private UsuarioModel solicitante;

    @OneToMany(mappedBy = "chamado")
    List<HistoricoChamadoModel> historicos;

    @ManyToOne
    @JoinColumn(name = "atendente_id")
    private UsuarioModel atendente;

    public String getNomeSolicitane() {
        return solicitante.getNome();
    }

    public String getNomeCategoria() {
        return categoria.getNomeCategoria();
    }
}
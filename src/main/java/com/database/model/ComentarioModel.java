package com.database.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;

import java.time.Instant;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comentario_chamado")
@SoftDelete(columnName = "inativo")
@Entity
public class ComentarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chamado_id")
    @JsonIgnore
    private ChamadoModel chamado;

    @ManyToOne
    @JoinColumn(name = "usuario_remetente_id")
    private UsuarioModel usuarioRemetente;

    @Column
    private String mensagem;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private Instant criadoEm;

    @ManyToOne
    @JoinColumn(name = "usuario_destinatario_id")
    private UsuarioModel usuarioDestinatario;

}

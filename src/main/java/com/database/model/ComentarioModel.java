package com.database.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SoftDelete;

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

    @ManyToOne
    @JoinColumn(name = "usuario_destinatario_id")
    private UsuarioModel usuarioDestinatario;

}

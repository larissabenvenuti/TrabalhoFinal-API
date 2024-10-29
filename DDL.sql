create database redesocial;

create table usuario (
	id_usuario serial primary key,
	nome varchar(60) not null,
	sobrenome varchar(100) not null,
	email varchar(100) unique not null,
	senha varchar(60) not null,
	data_nascimento date not null
);

create table postagem (
    id_postagem serial primary key,
    id_usuario int not null,
    conteudo varchar(255) not null,
    data_criacao date not null,
    constraint fk_usuario_postagem foreign key (id_usuario) references usuario (id_usuario)
);

create table comentario (
    id_comentario serial primary key,
    id_postagem int not null,
    comentario text not null,
    data_criacao date not null,
    constraint fk_postagem_comentario foreign key (id_postagem) references postagem (id_postagem)
);

create table relacionamento (
    seguidor_id int not null,
    seguido_id int not null,
    data_inicio_seguimento date not null,
    constraint pk_relacionamento primary key (seguidor_id, seguido_id),
    constraint fk_seguidor foreign key (seguidor_id) references usuario (id_usuario),
    constraint fk_seguido foreign key (seguido_id) references usuario (id_usuario)
);

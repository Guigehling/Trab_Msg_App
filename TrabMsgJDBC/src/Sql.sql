create sequence sequsuario;

create table usuario (
cod_user numeric(6),
login varchar(12) not null,
senha varchar(12) not null,
primary key (cod_user));

create sequence seqmensagens;

create table mensagens (
cod_msg numeric(6),
conteudo varchar(200) not null,
cod_emitente numeric(6),
cod_destinatario numeric(6),
primary key (cod_msg),
foreign key (cod_emitente) references usuario(cod_user),
foreign key (cod_destinatario) references usuario(cod_user));
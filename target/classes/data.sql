insert into produto
values(10001,'Mercearia', '87654321-B', 'Contem lactose', 'Parmalat', 'Leite Integral', 4.5, 1);
insert into produto
values(10002,'Perecíveis', '87654322-B', 'Fonte de fibras', 'Tio Joao', 'Arroz Integral', 5.5, 1);
insert into produto
values(10003,'Limpeza', '87654323-B', 'Limpeza profunda', 'OMO', 'Sabao em Po', 12, 1);
insert into produto
values(10004,'Limpeza', '87654324-C', 'Roupas e Limpeza', 'Dragao', 'Agua Sanitaria', 3, 1);
insert into produto
values(10005,'Higiene', '87654325-C', '9 a cada 10 dentistas recomendam', 'Colgate', 'Creme Dental', 2.5, 1);

insert into lote
values(1, '10/05/2021', 20, 10001);
insert into lote
values(2, '08/12/2025', 1, 10002);
insert into lote
values(3, '13/01/2023', 14, 10003);
insert into lote
values(4, '05/06/2021', 3, 10004);
insert into lote
values(5, '02/05/2022', 21, 10005);

insert into usuario
values(100);
insert into usuario
values(200);
insert into carrinho
values(100);
insert into carrinho
values(200);

insert into pedido
values(1,3,100,10004);
insert into pedido
values(2,7,200,10005);
insert into carrinho_pedidos
values(100,1);
insert into carrinho_pedidos
values(200,2);







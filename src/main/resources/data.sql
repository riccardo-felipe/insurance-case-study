insert into customer(id, name, email, document) values
  (1, 'Riccardo', 'riccardo@example.com', '00000000000'),
  (2, 'Felipe', 'felipe@example.com', '00000000001'),
  (3, 'Emília', 'emilia@example.com', '00000000002'),
  (4, 'Maria', 'maria@example.com', '00000000003')
;

insert into module(id, name, minimum_coverage, maximum_coverage, risk) values
  (1, 'Bike', 0.0, 3000.00, 0.30),
  (2, 'Jewelry', 500.00, 10000.00, 0.05),
  (3, 'Electronics', 500.00, 6000.00, 0.35),
  (4, 'Sports Equipment', 0.0, 20000.00, 0.30)
;

insert into insurance_policy(id, customer_id, module_id, selected_coverage) values
  (1, 1, 1, 2500.00),
  (2, 1, 2, 1000.00),
  (3, 1, 3, 9000.00),
  (4, 3, 4, 5500.00),
  (5, 3, 4, 1750.00)
;
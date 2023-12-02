INSERT INTO public.tb_training
(duration, unlock_xp, xp, training_id, category, classification, description, "name")
VALUES(45, 15, 70, '123e4567-e89b-12d3-a456-426614174000', 'HEALTH', 'IRON', 'Sessão de Pilates focada em fortalecimento e flexibilidade.', 'Pilates');

INSERT INTO public.tb_training
(duration, unlock_xp, xp, training_id, category, classification, description, "name")
VALUES(60, 20, 80, '123e4567-e89b-12d3-a456-426614174100', 'AEROBIC', 'BRONZE', 'Sessão de treino aeróbico intensivo para melhorar a resistência cardiovascular.', 'Treino Aeróbico Intensivo');

INSERT INTO public.tb_training
(duration, unlock_xp, xp, training_id, category, classification, description, "name")
VALUES(50, 15, 70, '123e4567-e89b-12d3-a456-426614174101', 'STRENGTH', 'SILVER', 'Série de exercícios de força focados em hipertrofia e aumento de força muscular.', 'Musculação Avançada');

INSERT INTO public.tb_training
(duration, unlock_xp, xp, training_id, category, classification, description, "name")
VALUES(30, 10, 50, '123e4567-e89b-12d3-a456-426614174102', 'HEALTH', 'GOLD', 'Sessão de exercícios leves focados em saúde geral e bem-estar.', 'Saúde e Bem-Estar');

INSERT INTO public.tb_training
(duration, unlock_xp, xp, training_id, category, classification, description, "name")
VALUES(45, 20, 75, '123e4567-e89b-12d3-a456-426614174103', 'FIGHT', 'DIAMOND', 'Treinamento de técnicas básicas de artes marciais para defesa pessoal.', 'Artes Marciais Básicas');


INSERT INTO public.tb_exercise
(id, training_training_id, exercise)
VALUES('123e4567-e89b-12d3-a456-426614174110', '123e4567-e89b-12d3-a456-426614174100', 'Corrida em Esteira');

INSERT INTO public.tb_exercise
(id, training_training_id, exercise)
VALUES('123e4567-e89b-12d3-a456-426614174111', '123e4567-e89b-12d3-a456-426614174100', 'Ciclismo Estacionário');



CREATE TABLE IF NOT EXISTS inventory
(
    item_id     UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name        VARCHAR(255)   NOT NULL,
    description TEXT,
    quantity    INT            not null check ( quantity >= 0 ),
    price       DECIMAL(10, 2) not null check ( price >= 0 )
)



-- Таблица организаторов
CREATE TABLE Organizer (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    contact_info TEXT
);

-- Таблица турниров
CREATE TABLE Tournament (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    start_date DATE,
    end_date DATE,
    type VARCHAR(50),
    organizer_id INTEGER REFERENCES Organizer(id)
);

-- Таблица игроков
CREATE TABLE Player (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    contact_info TEXT
);

-- Промежуточная таблица для участников турнира
CREATE TABLE Tournament_Player (
    tournament_id INTEGER REFERENCES Tournament(id),
    player_id INTEGER REFERENCES Player(id),
    PRIMARY KEY (tournament_id, player_id)
);

-- Таблица матчей
CREATE TABLE Match (
    id SERIAL PRIMARY KEY,
    tournament_id INTEGER REFERENCES Tournament(id),
    round INTEGER,
    player1_id INTEGER REFERENCES Player(id),
    player2_id INTEGER REFERENCES Player(id),
    winner_id INTEGER REFERENCES Player(id),
    score VARCHAR(50)
);

-- Таблица сеток
CREATE TABLE Bracket (
    id SERIAL PRIMARY KEY,
    tournament_id INTEGER REFERENCES Tournament(id),
    type VARCHAR(50),
    structure JSONB
);

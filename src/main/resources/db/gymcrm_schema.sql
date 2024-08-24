-- Table for User
CREATE TABLE "user" (
                        id SERIAL PRIMARY KEY,
                        first_name VARCHAR(50) NOT NULL,
                        last_name VARCHAR(50) NOT NULL,
                        username VARCHAR(100) UNIQUE NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        is_active BOOLEAN DEFAULT TRUE NOT NULL,
                        email VARCHAR(100) UNIQUE,
                        phone_number VARCHAR(20),
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table for Trainee
CREATE TABLE trainee (
                         id SERIAL PRIMARY KEY,
                         user_id INTEGER UNIQUE NOT NULL REFERENCES "user" (id) ON DELETE CASCADE,
                         date_of_birth DATE,
                         address VARCHAR(255),
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table for Trainer
CREATE TABLE trainer (
                         id SERIAL PRIMARY KEY,
                         user_id INTEGER UNIQUE NOT NULL REFERENCES "user" (id) ON DELETE CASCADE,
                         specialization VARCHAR(100),
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


-- Table for Training
CREATE TABLE training (
                          id SERIAL PRIMARY KEY,
                          trainee_id INTEGER NOT NULL REFERENCES trainee (id) ON DELETE SET NULL,
                          trainer_id INTEGER NOT NULL REFERENCES trainer (id) ON DELETE SET NULL,
                          training_name VARCHAR(100) NOT NULL,
                          training_type VARCHAR(100) NOT NULL,
                          training_date TIMESTAMP NOT NULL,
                          training_duration INTERVAL NOT NULL,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
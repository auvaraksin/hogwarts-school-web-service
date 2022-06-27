CREATE TABLE person (
                        id REAL,
                        person_name TEXT PRIMARY KEY,
                        person_age INTEGER,
                        driver_license BOOLEAN,
                        car_id REAL REFERENCES cars (id)
);

CREATE TABLE cars (
                      id REAL PRIMARY KEY,
                      brand_name TEXT UNIQUE,
                      model_name TEXT UNIQUE,
                      cost_value NUMERIC
);
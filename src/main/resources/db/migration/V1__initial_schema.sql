-- users and roles
CREATE TABLE roles (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE users (
  id BIGSERIAL PRIMARY KEY,
  username VARCHAR(100) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  full_name VARCHAR(255),
  email VARCHAR(255),
  nim VARCHAR(50),
  prodi VARCHAR(255),
  dosen_pembimbing VARCHAR(255),
  approved BOOLEAN DEFAULT FALSE,
  enabled BOOLEAN DEFAULT FALSE,
  created_at TIMESTAMP DEFAULT now()
);

CREATE TABLE user_roles (
  user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
  role_id BIGINT REFERENCES roles(id) ON DELETE RESTRICT,
  PRIMARY KEY(user_id, role_id)
);

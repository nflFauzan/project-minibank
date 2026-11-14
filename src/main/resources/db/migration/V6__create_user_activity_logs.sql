-- Flyway Migration V6: Create user activity logs table and indexes
CREATE TABLE IF NOT EXISTS user_activity_logs (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    user_full_name VARCHAR(200),
    user_role VARCHAR(50),
    activity_type VARCHAR(50) NOT NULL,
    module VARCHAR(30) NOT NULL,
    description VARCHAR(500) NOT NULL,
    reference_number VARCHAR(100),
    target_entity_id VARCHAR(100),
    amount NUMERIC(38, 2),
    status VARCHAR(30) DEFAULT 'SUCCESS',
    ip_address VARCHAR(50),
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_activity_username ON user_activity_logs(username);
CREATE INDEX IF NOT EXISTS idx_activity_timestamp ON user_activity_logs(timestamp);
CREATE INDEX IF NOT EXISTS idx_activity_type ON user_activity_logs(activity_type);
CREATE INDEX IF NOT EXISTS idx_activity_module ON user_activity_logs(module);

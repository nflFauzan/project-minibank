INSERT INTO users (id, username, password, full_name, approved, enabled, created_at)
VALUES (
    1,
    'admin',
    '$2a$10$5POFt3XZYy5ce1zBvdgr1O1tRcZIdnpCfDw2ZAOsncJDN7xt8o.Xa',  -- admin123
    'Administrator', 
    true, 
    true,
    NOW()
)
ON CONFLICT DO NOTHING;

INSERT INTO roles (id, name)
VALUES (1, 'ROLE_ADMIN')
ON CONFLICT DO NOTHING;

INSERT INTO user_roles (user_id, role_id)
VALUES (1, 1)
ON CONFLICT DO NOTHING;

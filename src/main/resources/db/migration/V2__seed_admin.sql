INSERT INTO users (username, password, full_name, enabled)
VALUES ('admin', '$2a$10$XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX', 'Administrator', true)
ON CONFLICT (username) DO NOTHING;

-- ganti password hash di atas dengan hash BCrypt untuk "minibank123" (generate di local)
-- lalu assign role:
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r
WHERE u.username='admin' AND r.name='ROLE_ADMIN'
ON CONFLICT DO NOTHING;

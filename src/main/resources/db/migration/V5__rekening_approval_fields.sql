-- V5: Add approval fields to rekening
ALTER TABLE rekening ADD COLUMN IF NOT EXISTS status VARCHAR(20) DEFAULT 'ACTIVE';
ALTER TABLE rekening ADD COLUMN IF NOT EXISTS approved_by VARCHAR(255);
ALTER TABLE rekening ADD COLUMN IF NOT EXISTS approved_at TIMESTAMP;
ALTER TABLE rekening ADD COLUMN IF NOT EXISTS approval_notes TEXT;
ALTER TABLE rekening ADD COLUMN IF NOT EXISTS rejection_reason TEXT;

UPDATE rekening SET status = 'ACTIVE' WHERE status IS NULL;


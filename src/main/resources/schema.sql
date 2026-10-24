-- Starsza baza miała kolumny content jako VARCHAR(255), a sformatowany mail jest dłuższy.
-- Na świeżej bazie tabel jeszcze nie ma, więc IF EXISTS po prostu nic nie robi.
ALTER TABLE IF EXISTS cron_entity ALTER COLUMN IF EXISTS content SET DATA TYPE VARCHAR(2000);
ALTER TABLE IF EXISTS inquiry_entity ALTER COLUMN IF EXISTS content SET DATA TYPE VARCHAR(2000);
ALTER TABLE IF EXISTS ticket_entity ALTER COLUMN IF EXISTS content SET DATA TYPE VARCHAR(2000);

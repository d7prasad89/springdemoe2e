-- Test seed data for songs table (includes length_in_seconds)
INSERT INTO songs (id, album, artist, is_favorite, title, length_in_seconds) VALUES (1, 'Album A', 'Artist A', FALSE, 'Song One', 210);
INSERT INTO songs (id, album, artist, is_favorite, title, length_in_seconds) VALUES (2, 'Album B', 'Artist B', TRUE, 'Song Two', 185);
INSERT INTO songs (id, album, artist, is_favorite, title, length_in_seconds) VALUES (3, 'Album C', 'Artist C', FALSE, 'Song Three', 240);

-- example update to modify length for a record (keeps test data dynamic if needed)
UPDATE songs SET length_in_seconds = 180 WHERE id = 2;


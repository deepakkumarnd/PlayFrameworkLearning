create KEYSPACE eva WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1 };

CREATE TABLE posts (id uuid, title text, content text, created_at timestamp, PRIMARY KEY (id, title)) ;
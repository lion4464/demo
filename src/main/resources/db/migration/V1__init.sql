CREATE TABLE story (
                         id UUID PRIMARY KEY,
                         title VARCHAR(255) NOT NULL
);

CREATE TABLE page (
                       id UUID PRIMARY KEY,
                       content TEXT,
                       reaction VARCHAR(20),
                       story_id UUID,
                       FOREIGN KEY (story_id) REFERENCES story(id)
);



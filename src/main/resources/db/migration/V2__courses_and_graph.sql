CREATE TABLE courses (
                         id              UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
                         title           VARCHAR(255) NOT NULL,
                         description     TEXT,
                         is_ai_generated BOOLEAN      NOT NULL DEFAULT FALSE,
                         created_at      TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE knowledge_nodes (
                                 id          UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
                                 course_id   UUID         NOT NULL REFERENCES courses(id) ON DELETE CASCADE,
                                 title       VARCHAR(255) NOT NULL,
                                 description TEXT,
                                 position_x  FLOAT,
                                 position_y  FLOAT
);

CREATE TABLE node_prerequisites (
                                    source_node_id UUID NOT NULL REFERENCES knowledge_nodes(id) ON DELETE CASCADE,
                                    target_node_id UUID NOT NULL REFERENCES knowledge_nodes(id) ON DELETE CASCADE,
                                    PRIMARY KEY (source_node_id, target_node_id),
                                    CHECK (source_node_id <> target_node_id)
);
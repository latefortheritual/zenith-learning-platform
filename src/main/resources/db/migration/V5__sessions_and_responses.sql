CREATE TABLE assessment_sessions (
                                     id            UUID              PRIMARY KEY DEFAULT gen_random_uuid(),
                                     user_id       UUID              NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                                     node_id       UUID              NOT NULL REFERENCES knowledge_nodes(id),
                                     session_type  node_session_type NOT NULL DEFAULT 'PRACTICE',
                                     theta_before  DOUBLE PRECISION  NOT NULL,
                                     theta_after   DOUBLE PRECISION,
                                     started_at    TIMESTAMP         NOT NULL DEFAULT NOW(),
                                     completed_at  TIMESTAMP
);

CREATE TABLE item_responses (
                                id                 UUID             PRIMARY KEY DEFAULT gen_random_uuid(),
                                session_id         UUID             NOT NULL REFERENCES assessment_sessions(id) ON DELETE CASCADE,
                                item_id            UUID             NOT NULL REFERENCES items(id),
                                selected_option_id UUID             REFERENCES answer_options(id),
                                is_correct         BOOLEAN          NOT NULL,
                                theta_at_response  DOUBLE PRECISION NOT NULL,
                                answered_at        TIMESTAMP        NOT NULL DEFAULT NOW()
);
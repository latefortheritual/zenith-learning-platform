CREATE INDEX idx_user_ability_user_course
    ON user_ability(user_id, course_id);

CREATE INDEX idx_node_states_user
    ON user_node_states(user_id);

CREATE INDEX idx_items_node_beta
    ON items(node_id, beta);

CREATE INDEX idx_sessions_user_node
    ON assessment_sessions(user_id, node_id);
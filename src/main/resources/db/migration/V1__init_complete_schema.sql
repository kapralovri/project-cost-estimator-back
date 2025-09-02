-- Создание основной таблицы estimates
CREATE TABLE estimates (
    id BIGSERIAL PRIMARY KEY,
    project_name VARCHAR(255) NOT NULL,
    client VARCHAR(255),
    currency VARCHAR(16) DEFAULT 'USD' NOT NULL,
    total_cost DECIMAL(19,2) DEFAULT 0 NOT NULL,
    quality_level VARCHAR(16) DEFAULT 'standard',
    status VARCHAR(32) DEFAULT 'Актуальный',
    parameters JSONB,
    tasks JSONB,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

-- Создание таблицы estimate_items
CREATE TABLE estimate_items (
    id BIGSERIAL PRIMARY KEY,
    estimate_id BIGINT NOT NULL,
    role VARCHAR(128) NOT NULL,
    hours DECIMAL(19,2) NOT NULL,
    rate DECIMAL(19,2) NOT NULL,
    cost DECIMAL(19,2) NOT NULL,
    CONSTRAINT fk_items_estimate FOREIGN KEY (estimate_id)
        REFERENCES estimates(id) ON DELETE CASCADE
);

-- Создание таблицы parameters
CREATE TABLE parameters (
    id BIGSERIAL PRIMARY KEY,
    estimate_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    param_value VARCHAR(1000) NOT NULL,
    type VARCHAR(50) DEFAULT 'string' NOT NULL,
    description TEXT,
    unit VARCHAR(50),
    min_value DECIMAL(19,2),
    max_value DECIMAL(19,2),
    is_required BOOLEAN DEFAULT FALSE NOT NULL,
    sort_order INTEGER DEFAULT 0 NOT NULL,
    CONSTRAINT fk_parameters_estimate FOREIGN KEY (estimate_id)
        REFERENCES estimates(id) ON DELETE CASCADE
);

-- Создание таблицы tasks
CREATE TABLE tasks (
    id BIGSERIAL PRIMARY KEY,
    estimate_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    category VARCHAR(100) DEFAULT 'development' NOT NULL,
    complexity VARCHAR(50) DEFAULT 'medium' NOT NULL,
    estimated_hours DECIMAL(19,2) DEFAULT 0 NOT NULL,
    actual_hours DECIMAL(19,2),
    status VARCHAR(50) DEFAULT 'planned' NOT NULL,
    priority VARCHAR(50) DEFAULT 'medium' NOT NULL,
    assigned_role VARCHAR(100),
    dependencies TEXT,
    start_date TIMESTAMP WITH TIME ZONE,
    due_date TIMESTAMP WITH TIME ZONE,
    completed_date TIMESTAMP WITH TIME ZONE,
    sort_order INTEGER DEFAULT 0 NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT fk_tasks_estimate FOREIGN KEY (estimate_id)
        REFERENCES estimates(id) ON DELETE CASCADE
);

-- Создание таблицы task_estimates
CREATE TABLE task_estimates (
    id BIGSERIAL PRIMARY KEY,
    task_id BIGINT NOT NULL,
    role VARCHAR(50) NOT NULL,
    min_value DECIMAL(19,2) NOT NULL DEFAULT 0,
    real_value DECIMAL(19,2) NOT NULL DEFAULT 0,
    max_value DECIMAL(19,2) NOT NULL DEFAULT 0,
    CONSTRAINT fk_task_estimates_task FOREIGN KEY (task_id)
        REFERENCES tasks(id) ON DELETE CASCADE
);

-- Создание индексов для оптимизации запросов
CREATE INDEX idx_items_estimate ON estimate_items(estimate_id);
CREATE INDEX idx_parameters_estimate ON parameters(estimate_id);
CREATE INDEX idx_parameters_sort ON parameters(estimate_id, sort_order);
CREATE INDEX idx_tasks_estimate ON tasks(estimate_id);
CREATE INDEX idx_tasks_sort ON tasks(estimate_id, sort_order);
CREATE INDEX idx_tasks_status ON tasks(estimate_id, status);
CREATE INDEX idx_tasks_category ON tasks(estimate_id, category);
CREATE INDEX idx_task_estimates_task ON task_estimates(task_id);
CREATE INDEX idx_task_estimates_role ON task_estimates(task_id, role);

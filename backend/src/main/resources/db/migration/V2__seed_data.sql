-- Departments
INSERT INTO departments (id, name, description) VALUES
(1, 'Engineering',      'Software engineering and platform team'),
(2, 'Product',          'Product management and design'),
(3, 'Data & Analytics', 'Data engineering, BI, and data science'),
(4, 'DevOps',           'Infrastructure, CI/CD, and reliability'),
(5, 'QA',               'Quality assurance and test automation');

SELECT setval('departments_id_seq', 5);

-- Employees (10 per department)
INSERT INTO employees (id, first_name, last_name, email, hire_date, department_id) VALUES
-- Engineering (dept 1)
(1,  'Alice',   'Johnson',   'alice.johnson@taskflow.io',   '2021-03-15', 1),
(2,  'Bob',     'Martinez',  'bob.martinez@taskflow.io',    '2020-07-01', 1),
(3,  'Carol',   'Williams',  'carol.williams@taskflow.io',  '2022-01-10', 1),
(4,  'David',   'Brown',     'david.brown@taskflow.io',     '2019-11-20', 1),
(5,  'Eva',     'Davis',     'eva.davis@taskflow.io',       '2023-05-08', 1),
(6,  'Frank',   'Wilson',    'frank.wilson@taskflow.io',    '2021-09-14', 1),
(7,  'Grace',   'Taylor',    'grace.taylor@taskflow.io',    '2022-06-30', 1),
(8,  'Henry',   'Anderson',  'henry.anderson@taskflow.io',  '2020-02-28', 1),
(9,  'Iris',    'Thomas',    'iris.thomas@taskflow.io',     '2023-01-16', 1),
(10, 'James',   'Jackson',   'james.jackson@taskflow.io',   '2021-08-03', 1),
-- Product (dept 2)
(11, 'Karen',   'White',     'karen.white@taskflow.io',     '2020-04-22', 2),
(12, 'Leo',     'Harris',    'leo.harris@taskflow.io',      '2022-09-05', 2),
(13, 'Maya',    'Martin',    'maya.martin@taskflow.io',     '2021-12-11', 2),
(14, 'Nathan',  'Garcia',    'nathan.garcia@taskflow.io',   '2019-06-17', 2),
(15, 'Olivia',  'Martinez',  'olivia.martinez@taskflow.io', '2023-03-29', 2),
(16, 'Paul',    'Robinson',  'paul.robinson@taskflow.io',   '2020-10-07', 2),
(17, 'Quinn',   'Clark',     'quinn.clark@taskflow.io',     '2022-04-18', 2),
(18, 'Rachel',  'Rodriguez', 'rachel.rodriguez@taskflow.io','2021-07-25', 2),
(19, 'Sam',     'Lewis',     'sam.lewis@taskflow.io',       '2023-08-12', 2),
(20, 'Tina',    'Lee',       'tina.lee@taskflow.io',        '2020-01-14', 2),
-- Data & Analytics (dept 3)
(21, 'Uma',     'Walker',    'uma.walker@taskflow.io',      '2021-05-19', 3),
(22, 'Victor',  'Hall',      'victor.hall@taskflow.io',     '2022-11-02', 3),
(23, 'Wendy',   'Allen',     'wendy.allen@taskflow.io',     '2020-08-24', 3),
(24, 'Xander',  'Young',     'xander.young@taskflow.io',    '2023-02-06', 3),
(25, 'Yara',    'Hernandez', 'yara.hernandez@taskflow.io',  '2021-04-13', 3),
(26, 'Zach',    'King',      'zach.king@taskflow.io',       '2019-09-30', 3),
(27, 'Amy',     'Wright',    'amy.wright@taskflow.io',      '2022-07-21', 3),
(28, 'Ben',     'Lopez',     'ben.lopez@taskflow.io',       '2020-12-09', 3),
(29, 'Chloe',   'Hill',      'chloe.hill@taskflow.io',      '2023-06-27', 3),
(30, 'Dylan',   'Scott',     'dylan.scott@taskflow.io',     '2021-10-15', 3),
-- DevOps (dept 4)
(31, 'Elena',   'Green',     'elena.green@taskflow.io',     '2020-05-01', 4),
(32, 'Felix',   'Adams',     'felix.adams@taskflow.io',     '2022-02-14', 4),
(33, 'Gina',    'Baker',     'gina.baker@taskflow.io',      '2021-11-08', 4),
(34, 'Hugo',    'Gonzalez',  'hugo.gonzalez@taskflow.io',   '2023-04-03', 4),
(35, 'Isla',    'Nelson',    'isla.nelson@taskflow.io',     '2019-12-22', 4),
(36, 'Jake',    'Carter',    'jake.carter@taskflow.io',     '2022-08-16', 4),
(37, 'Kira',    'Mitchell',  'kira.mitchell@taskflow.io',   '2021-01-31', 4),
(38, 'Liam',    'Perez',     'liam.perez@taskflow.io',      '2020-09-18', 4),
(39, 'Mia',     'Roberts',   'mia.roberts@taskflow.io',     '2023-07-07', 4),
(40, 'Noah',    'Turner',    'noah.turner@taskflow.io',     '2021-06-24', 4),
-- QA (dept 5)
(41, 'Aria',    'Phillips',  'aria.phillips@taskflow.io',   '2020-03-10', 5),
(42, 'Blake',   'Campbell',  'blake.campbell@taskflow.io',  '2022-10-26', 5),
(43, 'Casey',   'Parker',    'casey.parker@taskflow.io',    '2021-02-17', 5),
(44, 'Drew',    'Evans',     'drew.evans@taskflow.io',      '2019-07-04', 5),
(45, 'Elle',    'Edwards',   'elle.edwards@taskflow.io',    '2023-09-20', 5),
(46, 'Finn',    'Collins',   'finn.collins@taskflow.io',    '2020-11-13', 5),
(47, 'Gem',     'Stewart',   'gem.stewart@taskflow.io',     '2022-03-08', 5),
(48, 'Hana',    'Sanchez',   'hana.sanchez@taskflow.io',    '2021-08-29', 5),
(49, 'Ivan',    'Morris',    'ivan.morris@taskflow.io',     '2023-11-11', 5),
(50, 'Jade',    'Rogers',    'jade.rogers@taskflow.io',     '2020-06-05', 5);

SELECT setval('employees_id_seq', 50);

-- Projects
INSERT INTO projects (id, name, description, start_date, end_date, status) VALUES
(1,  'Platform Revamp',        'Re-architect the core platform for scalability',       '2024-01-01', '2024-12-31', 'ACTIVE'),
(2,  'Mobile App v2',          'Native mobile app rewrite in React Native',             '2024-02-15', '2024-09-30', 'ACTIVE'),
(3,  'Data Warehouse',         'Centralised analytics data warehouse on Snowflake',     '2024-01-10', '2025-03-31', 'ACTIVE'),
(4,  'CI/CD Pipeline',         'Automated CI/CD with GitHub Actions and ArgoCD',        '2024-03-01', '2024-08-31', 'COMPLETED'),
(5,  'Customer Portal',        'Self-service portal for enterprise customers',           '2024-04-01', '2025-01-31', 'ACTIVE'),
(6,  'API Gateway',            'Centralised API gateway with rate limiting',             '2024-02-01', '2024-10-31', 'ACTIVE'),
(7,  'Auth Service',           'OAuth2/OIDC identity service',                          '2023-11-01', '2024-06-30', 'COMPLETED'),
(8,  'Notification Engine',    'Real-time push and email notification system',           '2024-05-01', '2024-11-30', 'ACTIVE'),
(9,  'Search Indexer',         'Full-text search powered by Elasticsearch',             '2024-03-15', '2024-12-15', 'ACTIVE'),
(10, 'Report Generator',       'Scheduled and on-demand PDF/Excel report exports',      '2024-06-01', '2025-02-28', 'ACTIVE'),
(11, 'Payment Integration',    'Stripe and PayPal payment gateway integration',          '2024-01-20', '2024-07-31', 'COMPLETED'),
(12, 'Admin Dashboard',        'Internal admin tooling for ops team',                   '2024-04-15', '2024-10-15', 'ACTIVE'),
(13, 'Performance Profiling',  'APM integration with Datadog and custom traces',        '2024-02-10', '2024-09-10', 'ACTIVE'),
(14, 'Multi-tenancy',          'Tenant isolation and RBAC across all services',         '2024-03-20', '2025-06-30', 'ACTIVE'),
(15, 'Data Migration',         'Legacy DB migration to PostgreSQL with zero downtime',  '2024-01-05', '2024-05-31', 'COMPLETED'),
(16, 'WebSocket Service',      'Real-time collaboration via WebSocket',                 '2024-07-01', '2025-01-31', 'ACTIVE'),
(17, 'Audit Logging',          'Immutable audit log for compliance requirements',       '2024-05-15', '2024-11-15', 'ACTIVE'),
(18, 'SSO Integration',        'SAML 2.0 SSO for enterprise customers',                 '2024-06-10', '2025-03-10', 'ACTIVE'),
(19, 'CDN Setup',              'Global CDN rollout for static assets',                  '2024-04-01', '2024-07-31', 'COMPLETED'),
(20, 'Chaos Engineering',      'Resilience testing with fault injection',               '2024-08-01', '2025-02-01', 'ACTIVE');

SELECT setval('projects_id_seq', 20);

INSERT INTO tasks (title, description, status, priority, estimated_hours, actual_hours, due_date, created_at, project_id, assigned_to_id)
SELECT
    'Task #' || i                                        AS title,
    'Implement and verify the deliverable for task ' || i AS description,
    (ARRAY['TODO', 'IN_PROGRESS', 'IN_REVIEW', 'DONE'])[(i % 4) + 1] AS status,
    (ARRAY['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'])[(i % 4) + 1]        AS priority,
    (i % 8) + 1                                          AS estimated_hours,
    CASE WHEN i % 3 = 0 THEN (i % 6) + 1 ELSE NULL END  AS actual_hours,
    CURRENT_DATE + ((i % 90) - 30)                       AS due_date,
    NOW() - ((i % 365) || ' days')::INTERVAL             AS created_at,
    (i % 20) + 1                                         AS project_id,
    (i % 50) + 1                                         AS assigned_to_id
FROM generate_series(1, 50000) AS i;

SELECT setval('tasks_id_seq', 50000);

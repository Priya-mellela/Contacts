CREATE TABLE contacts (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          email VARCHAR(255),
                          phone VARCHAR(50),
                          address VARCHAR(255),
                          position VARCHAR(100),
                          created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

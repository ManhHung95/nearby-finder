-- Enable PostGIS extension
CREATE EXTENSION IF NOT EXISTS postgis;

-- Create places table
CREATE TABLE places (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name TEXT NOT NULL,
    type TEXT NOT NULL,
    address TEXT,
    location GEOGRAPHY(Point, 4326) NOT NULL,
    rating NUMERIC(2,1),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create spatial index for efficient radius queries
CREATE INDEX idx_places_location ON places USING GIST (location);

-- Create index for type filtering
CREATE INDEX idx_places_type ON places(type);

-- Insert sample data
INSERT INTO places (name, type, address, location, rating) VALUES
('ABC Coffee', 'restaurant', '123 Main St', ST_SetSRID(ST_MakePoint(106.6921, 10.7712), 4326)::geography, 4.4),
('City Hospital', 'hospital', '456 Health Ave', ST_SetSRID(ST_MakePoint(106.6950, 10.7750), 4326)::geography, 4.2),
('Quick ATM', 'atm', '789 Bank St', ST_SetSRID(ST_MakePoint(106.6900, 10.7700), 4326)::geography, null),
('Pizza Palace', 'restaurant', '321 Food Court', ST_SetSRID(ST_MakePoint(106.6980, 10.7730), 4326)::geography, 4.6),
('Metro Pharmacy', 'pharmacy', '654 Care Blvd', ST_SetSRID(ST_MakePoint(106.6890, 10.7680), 4326)::geography, 4.1);
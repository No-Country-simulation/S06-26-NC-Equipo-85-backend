ALTER TABLE experience
ADD COLUMN mentor_profile_id UUID NOT NULL;

ALTER TABLE experience
ADD CONSTRAINT fk_experience_mentor_profile
FOREIGN KEY (mentor_profile_id)
REFERENCES profiles(id);

ALTER TABLE experience
ADD COLUMN created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW();

ALTER TABLE experience
ADD COLUMN updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW();
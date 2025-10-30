import os

class Config:
    # PostgreSQL en puerto 5432 (default)
    SQLALCHEMY_DATABASE_URI = 'postgresql://cyberlearn_user:password@localhost:5432/cyberlearn_db'
    SQLALCHEMY_TRACK_MODIFICATIONS = False
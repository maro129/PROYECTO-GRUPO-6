from database.db import db

class Badge(db.Model):
    id = db.Column(db.String(50), primary_key=True)
    name = db.Column(db.String(100), nullable=False)
    description = db.Column(db.String(255))
    xp_required = db.Column(db.Integer, default=0)
    icon = db.Column(db.String(50))
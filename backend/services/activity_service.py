from models.user import User
from models.activity import UserActivity
from database.db import db
from datetime import datetime, timedelta

class ActivityService:
    @staticmethod
    def record_activity(user_id, activity_type, lesson_id=None, difficulty=1):
        # Calcular XP usando fórmula: (Dificultad * 10) + (Racha * 2)
        user = User.query.get(user_id)
        streak = ActivityService._calculate_streak(user)
        xp_earned = (difficulty * 10) + (streak * 2)
        
        # Crear actividad
        activity = UserActivity(
            user_id=user_id,
            activity_type=activity_type,
            xp_earned=xp_earned,
            lesson_id=lesson_id,
            difficulty=difficulty
        )
        
        # Actualizar usuario
        user.xp_total += xp_earned
        user.level = ActivityService._calculate_level(user.xp_total)
        user.streak = streak
        user.last_activity = datetime.utcnow()
        
        db.session.add(activity)
        db.session.commit()
        
        return {
            'xp_earned': xp_earned,
            'new_total_xp': user.xp_total,
            'new_level': user.level,
            'streak': streak
        }
    
    @staticmethod
    def _calculate_streak(user):
        if not user.last_activity:
            return 1
        
        today = datetime.utcnow().date()
        last_activity_date = user.last_activity.date()
        
        if last_activity_date == today:
            return user.streak
        elif (today - last_activity_date).days == 1:
            return user.streak + 1
        else:
            return 1
    
    @staticmethod
    def _calculate_level(xp_total):
        level = 1
        xp_required = 0
        while xp_total >= xp_required:
            level += 1
            xp_required += level * 100
        return level - 1
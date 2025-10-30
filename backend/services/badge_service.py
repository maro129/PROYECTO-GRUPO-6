from models.user import User, UserBadge
from models.badge import Badge
from database.db import db

class BadgeService:
    @staticmethod
    def get_user_badges(user_id):
        user_badges = UserBadge.query.filter_by(user_id=user_id).all()
        return [
            {
                'id': ub.badge_id,
                'earned_at': ub.earned_at.isoformat() if ub.earned_at else None
            }
            for ub in user_badges
        ]
    
    @staticmethod
    def check_and_award_badges(user_id):
        user = User.query.get(user_id)
        badges_to_award = []
        
        # Badge por primera lección
        if user.xp_total >= 10 and not BadgeService._has_badge(user_id, 'first_lesson'):
            badges_to_award.append('first_lesson')
        
        # Badge por racha de 7 días
        if user.streak >= 7 and not BadgeService._has_badge(user_id, 'weekly_streak'):
            badges_to_award.append('weekly_streak')
        
        # Badge por 1000 XP
        if user.xp_total >= 1000 and not BadgeService._has_badge(user_id, 'thousand_xp'):
            badges_to_award.append('thousand_xp')
        
        # Otorgar badges
        for badge_id in badges_to_award:
            user_badge = UserBadge(user_id=user_id, badge_id=badge_id)
            db.session.add(user_badge)
        
        if badges_to_award:
            db.session.commit()
        
        return badges_to_award
    
    @staticmethod
    def _has_badge(user_id, badge_id):
        return UserBadge.query.filter_by(user_id=user_id, badge_id=badge_id).first() is not None
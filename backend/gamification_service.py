from auth_service import AuthService
from datetime import datetime

class GamificationService:
    def __init__(self):
        self.auth_service = AuthService()
    
    def get_user_progress(self, email):
        users = self.auth_service._load_users()
        user = users.get(email)
        
        if not user:
            return {'error': 'Usuario no encontrado'}
        
        # Calcular nivel basado en XP
        level = self._calculate_level(user.get('xp_total', 0))
        
        # Calcular racha
        streak = self._calculate_streak(user)
        
        return {
            'name': user.get('name', 'Usuario'),
            'email': email,
            'level': level,
            'xpTotal': user.get('xp_total', 0),
            'streak': streak,
            'badges': user.get('badges', []),
            'lessonsCompleted': user.get('lessons_completed', 0),
            'coursesCompleted': user.get('courses_completed', 0),
            'nextLevelXp': self._get_next_level_xp(level),
            'progressPercentage': self._calculate_progress_percentage(user.get('xp_total', 0))
        }
    
    def _calculate_level(self, xp_total):
        if xp_total < 100: return 1
        elif xp_total < 300: return 2
        elif xp_total < 600: return 3
        elif xp_total < 1000: return 4
        elif xp_total < 1500: return 5
        elif xp_total < 2100: return 6
        elif xp_total < 2800: return 7
        elif xp_total < 3600: return 8
        elif xp_total < 4500: return 9
        else: return 10
    
    def _calculate_streak(self, user):
        if not user.get('last_login'):
            return 0
        
        try:
            last_login = datetime.fromisoformat(user['last_login'])
            today = datetime.now().date()
            last_login_date = last_login.date()
            
            if last_login_date == today:
                return user.get('streak', 0)
            elif (today - last_login_date).days == 1:
                return user.get('streak', 0) + 1
            else:
                return 1
        except:
            return user.get('streak', 0)
    
    def _get_next_level_xp(self, current_level):
        level_xp = [0, 100, 300, 600, 1000, 1500, 2100, 2800, 3600, 4500]
        if current_level < len(level_xp):
            return level_xp[current_level]
        return level_xp[-1] + (current_level - 9) * 1000
    
    def _calculate_progress_percentage(self, xp_total):
        max_xp_considered = 5000
        return min((xp_total / max_xp_considered) * 100, 100)
    
    def get_user_achievements(self, email):
        users = self.auth_service._load_users()
        user = users.get(email)
        
        if not user:
            return {'error': 'Usuario no encontrado'}
        
        achievements = []
        xp_total = user.get('xp_total', 0)
        lessons_completed = user.get('lessons_completed', 0)
        streak = user.get('streak', 0)
        
        # Logros basados en progreso
        if lessons_completed >= 1:
            achievements.append({
                'id': 'first_lesson',
                'title': 'Primera Lección',
                'description': 'Completaste tu primera lección',
                'icon': '📚',
                'unlocked': True,
                'progress': 100
            })
        
        if streak >= 7:
            achievements.append({
                'id': 'weekly_streak', 
                'title': 'Racha Semanal',
                'description': '7 días consecutivos aprendiendo',
                'icon': '🔥',
                'unlocked': True,
                'progress': 100
            })
        
        if xp_total >= 1000:
            achievements.append({
                'id': 'thousand_xp',
                'title': '1000 XP',
                'description': 'Alcanzaste 1000 puntos de experiencia',
                'icon': '💪',
                'unlocked': True,
                'progress': 100
            })
        
        # Logros bloqueados (para mostrar próximos)
        if lessons_completed < 1:
            achievements.append({
                'id': 'first_lesson',
                'title': 'Primera Lección',
                'description': 'Completa tu primera lección',
                'icon': '📚',
                'unlocked': False,
                'progress': 0
            })
        
        if streak < 7:
            achievements.append({
                'id': 'weekly_streak',
                'title': 'Racha Semanal', 
                'description': 'Mantén 7 días consecutivos',
                'icon': '🔥',
                'unlocked': False,
                'progress': min((streak / 7) * 100, 100)
            })
        
        return achievements
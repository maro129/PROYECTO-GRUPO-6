class CourseService:
    def get_all_courses(self):
        return [
            {
                'id': 'crypto_basics',
                'title': 'Criptografía Básica',
                'description': 'Aprende los fundamentos de encriptación y seguridad',
                'level': 'Principiante',
                'xp_reward': 120,
                'progress': 65,
                'image_url': '🔐',
                'lessons_total': 5,
                'lessons_completed': 3
            },
            {
                'id': 'ethical_hacking',
                'title': 'Ethical Hacking',
                'description': 'Técnicas de hacking ético y pruebas de penetración',
                'level': 'Intermedio', 
                'xp_reward': 80,
                'progress': 30,
                'image_url': '🛡️',
                'lessons_total': 8,
                'lessons_completed': 2
            },
            {
                'id': 'ai_security',
                'title': 'IA para Seguridad',
                'description': 'Aplicación de inteligencia artificial en ciberseguridad',
                'level': 'Avanzado',
                'xp_reward': 150,
                'progress': 0,
                'image_url': '🤖',
                'lessons_total': 6,
                'lessons_completed': 0
            },
            {
                'id': 'malware_analysis',
                'title': 'Análisis de Malware',
                'description': 'Técnicas avanzadas para analizar software malicioso',
                'level': 'Avanzado',
                'xp_reward': 200,
                'progress': 0,
                'image_url': '🦠',
                'lessons_total': 7,
                'lessons_completed': 0,
                'locked': True
            }
        ]
    
    def get_course_by_id(self, course_id):
        courses = self.get_all_courses()
        for course in courses:
            if course['id'] == course_id:
                return course
        return None
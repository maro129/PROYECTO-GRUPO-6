from flask import Flask, jsonify, request
from flask_cors import CORS
from auth_service import AuthService
from gamification_service import GamificationService
from course_service import CourseService
import json
import os
from services.activity_service import ActivityService
from services.badge_service import BadgeService
from models.user import User
from functools import wraps

# ✅ IMPORTACIONES NUEVAS - con manejo de errores para compatibilidad
try:
    from database.db import db, init_db
    from config import Config
    DB_AVAILABLE = True
    print("✅ Módulos de base de datos cargados correctamente")
except ImportError as e:
    print(f"🔧 Modo JSON - Base de datos no disponible: {e}")
    DB_AVAILABLE = False

app = Flask(__name__)
CORS(app)

# ✅ INICIALIZAR BASE DE DATOS SOLO SI ESTÁ DISPONIBLE
if DB_AVAILABLE:
    try:
        app.config.from_object(Config)
        init_db(app)
        print("✅ Base de datos PostgreSQL inicializada")
    except Exception as e:
        print(f"❌ Error inicializando base de datos: {e}")
        DB_AVAILABLE = False
else:
    print("🔧 Ejecutando en modo JSON")

# Inicializar servicios
auth_service = AuthService()
gamification_service = GamificationService()
course_service = CourseService()

def token_required(f):
    @wraps(f)
    def decorated(*args, **kwargs):
        token = request.headers.get('Authorization', '').replace('Bearer ', '')
        user_data = auth_service.verify_token(token)
        
        if not user_data:
            return jsonify({'error': 'Token inválido'}), 401
            
        return f(user_data, *args, **kwargs)
    return decorated

@app.route('/api/auth/register', methods=['POST'])
def register():
    try:
        data = request.get_json()
        email = data.get('email')
        password = data.get('password')
        name = data.get('name', 'Usuario')
        
        result = auth_service.register(email, password, name)
        return jsonify(result)
    except Exception as e:
        return jsonify({'success': False, 'message': str(e)}), 400

@app.route('/api/auth/login', methods=['POST'])
def login():
    try:
        data = request.get_json()
        email = data.get('email')
        password = data.get('password')
        
        result = auth_service.login(email, password)
        return jsonify(result)
    except Exception as e:
        return jsonify({'success': False, 'message': str(e)}), 401

@app.route('/api/user/progress', methods=['GET'])
def get_user_progress():
    try:
        token = request.headers.get('Authorization', '').replace('Bearer ', '')
        user_data = auth_service.verify_token(token)
        
        if not user_data:
            return jsonify({'error': 'Token inválido'}), 401
            
        progress = gamification_service.get_user_progress(user_data['email'])
        return jsonify(progress)
    except Exception as e:
        return jsonify({'error': str(e)}), 400

@app.route('/api/courses', methods=['GET'])
def get_courses():
    try:
        token = request.headers.get('Authorization', '').replace('Bearer ', '')
        user_data = auth_service.verify_token(token)
        
        if not user_data:
            return jsonify({'error': 'Token inválido'}), 401
            
        courses = course_service.get_all_courses()
        return jsonify(courses)
    except Exception as e:
        return jsonify({'error': str(e)}), 400

@app.route('/api/courses/<course_id>', methods=['GET'])
def get_course_detail(course_id):
    try:
        token = request.headers.get('Authorization', '').replace('Bearer ', '')
        user_data = auth_service.verify_token(token)
        
        if not user_data:
            return jsonify({'error': 'Token inválido'}), 401
            
        course = course_service.get_course_by_id(course_id)
        return jsonify(course)
    except Exception as e:
        return jsonify({'error': str(e)}), 400

@app.route('/api/achievements', methods=['GET'])
def get_achievements():
    try:
        token = request.headers.get('Authorization', '').replace('Bearer ', '')
        user_data = auth_service.verify_token(token)
        
        if not user_data:
            return jsonify({'error': 'Token inválido'}), 401
            
        achievements = gamification_service.get_user_achievements(user_data['email'])
        return jsonify(achievements)
    except Exception as e:
        return jsonify({'error': str(e)}), 400

@app.route('/api/user/complete-activity', methods=['POST'])
@token_required
def complete_activity(current_user):
    try:
        data = request.get_json()
        activity_type = data.get('type', 'lesson_completed')
        lesson_id = data.get('lesson_id')
        difficulty = data.get('difficulty', 1)
        
        # Obtener user_id del email
        user = User.query.filter_by(email=current_user['email']).first()
        if not user:
            return jsonify({'error': 'Usuario no encontrado'}), 404
        
        result = ActivityService.record_activity(
            user.id, activity_type, lesson_id, difficulty
        )
        
        # Verificar badges
        new_badges = BadgeService.check_and_award_badges(user.id)
        
        return jsonify({
            'success': True,
            'activity_result': result,
            'new_badges': new_badges
        })
        
    except Exception as e:
        return jsonify({'error': str(e)}), 400

@app.route('/api/user/badges', methods=['GET'])
@token_required
def get_user_badges(current_user):
    try:
        user = User.query.filter_by(email=current_user['email']).first()
        if not user:
            return jsonify({'error': 'Usuario no encontrado'}), 404
        
        badges = BadgeService.get_user_badges(user.id)
        return jsonify({'badges': badges})
        
    except Exception as e:
        return jsonify({'error': str(e)}), 400

@app.route('/')
def home():
    return jsonify({
        'message': '🚀 CyberLearn Backend funcionando!',
        'version': '1.0',
        'endpoints': [
            'POST /api/auth/register',
            'POST /api/auth/login', 
            'GET /api/user/progress',
            'GET /api/courses',
            'GET /api/achievements'
        ]
    })

if __name__ == '__main__':
    print("=" * 60)
    print("🔥 CYBERLEARN BACKEND - INICIANDO SERVIDOR")
    print("📍 URL Local: http://localhost:5000")
    print("📱 URL Android: http://10.0.2.2:5000")
    print("📚 Endpoints disponibles:")
    print("   POST /api/auth/register")
    print("   POST /api/auth/login")
    print("   GET /api/user/progress") 
    print("   GET /api/courses")
    print("   GET /api/achievements")
    print("=" * 60)
    app.run(debug=True, port=5000, host='0.0.0.0')
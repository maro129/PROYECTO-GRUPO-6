from flask import Flask, jsonify, request
from flask_cors import CORS
# 1. Importación necesaria
from flask_sqlalchemy import SQLAlchemy

# --- CONFIGURACIÓN DE LA APP Y LA BASE DE DATOS ---
app = Flask(__name__)

# 2. Definir la URI de la BD
# Formato: postgresql://<user>:<password>@<host>:<port>/<database_name>
app.config['SQLALCHEMY_DATABASE_URI'] = 'postgresql://<user>:<password>@<host>:5432/<database_name>'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

# 3. Inicializar SQLAlchemy
db = SQLAlchemy(app)

# Modelos para SQLAlchemy (Tablas de la Base de Datos)

# Tabla para registrar las acciones de los usuarios
class Activity(db.Model):
    __tablename__ = 'activity'
    id = db.Column(db.Integer, primary_key=True)
    user_id = db.Column(db.Integer, nullable=False)
    type = db.Column(db.String(80), nullable=False)
    points_awarded = db.Column(db.Integer, default=0)
    timestamp = db.Column(db.DateTime, default=db.func.current_timestamp())

# Tabla para definir las insignias
class Badge(db.Model):
    __tablename__ = 'badge'
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(80), unique=True, nullable=False)
    description = db.Column(db.String(255))
    required_activities = db.Column(db.Integer, default=1)

# Tabla para rastrear qué insignias ha ganado cada usuario
class UserBadge(db.Model):
    __tablename__ = 'user_badge'
    id = db.Column(db.Integer, primary_key=True)
    user_id = db.Column(db.Integer, nullable=False)
    badge_id = db.Column(db.Integer, db.ForeignKey('badge.id'), nullable=False)
    awarded_date = db.Column(db.DateTime, default=db.func.current_timestamp())

    # Evita que un usuario obtenga la misma insignia dos veces
    __table_args__ = (db.UniqueConstraint('user_id', 'badge_id', name='_user_badge_uc'),)
from auth_service import AuthService
from gamification_service import GamificationService
from course_service import CourseService
import json
import os

app = Flask(__name__)
CORS(app)

# Inicializar servicios
auth_service = AuthService()
gamification_service = GamificationService()
course_service = CourseService()

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
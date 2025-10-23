import hashlib
import json
import os
from datetime import datetime, timedelta

class AuthService:
    def __init__(self):
        self.data_file = 'data/users.json'
        self._ensure_data_file()
    
    def _ensure_data_file(self):
        if not os.path.exists('data'):
            os.makedirs('data')
        if not os.path.exists(self.data_file):
            with open(self.data_file, 'w') as f:
                json.dump({}, f)
    
    def _load_users(self):
        try:
            with open(self.data_file, 'r') as f:
                return json.load(f)
        except:
            return {}
    
    def _save_users(self, users):
        with open(self.data_file, 'w') as f:
            json.dump(users, f, indent=2)
    
    def is_strong_password(self, password):
        return (len(password) >= 8 and 
                any(c.isupper() for c in password) and 
                any(c.isdigit() for c in password))
    
    def _hash_password(self, password):
        return hashlib.sha256(password.encode()).hexdigest()
    
    def register(self, email, password, name):
        if not self.is_strong_password(password):
            return {
                'success': False, 
                'message': 'Contraseña débil. Usa mayúsculas, números y mínimo 8 caracteres.'
            }
        
        users = self._load_users()
        
        if email in users:
            return {
                'success': False, 
                'message': 'El usuario ya existe'
            }
        
        users[email] = {
            'email': email,
            'password_hash': self._hash_password(password),
            'name': name,
            'created_at': datetime.now().isoformat(),
            'xp_total': 0,
            'level': 1,
            'streak': 0,
            'last_login': None,
            'badges': [],
            'lessons_completed': 0,
            'courses_completed': 0
        }
        
        self._save_users(users)
        
        return {
            'success': True,
            'message': 'Usuario registrado correctamente',
            'token': f"token-{email}-{datetime.now().timestamp()}",
            'user': {
                'email': email,
                'name': name
            }
        }
    
    def login(self, email, password):
        users = self._load_users()
        user = users.get(email)
        
        if not user or user['password_hash'] != self._hash_password(password):
            return {
                'success': False,
                'message': 'Credenciales inválidas'
            }
        
        # Actualizar última conexión
        user['last_login'] = datetime.now().isoformat()
        self._save_users(users)
        
        return {
            'success': True,
            'message': 'Autenticación exitosa',
            'token': f"token-{email}-{datetime.now().timestamp()}",
            'user': {
                'email': email,
                'name': user['name']
            }
        }
    
    def verify_token(self, token):
        try:
            # Simulación simple de verificación de token
            if token.startswith('token-'):
                email_part = token.split('-')[1]
                users = self._load_users()
                if email_part in users:
                    return {'email': email_part}
            return None
        except:
            return None
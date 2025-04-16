import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_URL } from '../utils/constants'; // URL do backend

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = API_URL; // URL do backend Spring Boot
  private tokenKey = 'auth-token';  // Chave para armazenar o token
  private userKey = 'user';  // Chave para armazenar dados do usuário

  constructor(private http: HttpClient) {}

  // Função para realizar o login
  login(email: string, password: string): Observable<any> {
    const loginData = { email, password };

    return this.http.post<any>(`${this.apiUrl}/users/login`, loginData);
  }

  // Armazena o token no localStorage
  saveToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
  }

  // Armazena os dados do usuário no localStorage
  saveUser(user: any): void {
    localStorage.setItem(this.userKey, JSON.stringify(user));
  }

  // Recupera o token armazenado
  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  // Verifica se o usuário está autenticado (se o token existe)
  isAuthenticated(): boolean {
    return !!this.getToken();
  }

  // Realiza o logout
  logout(): void {
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.userKey);
  }
}


import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_URL } from '../utils/constants'; // URL da API do backend

@Injectable({
  providedIn: 'root',
})
export class RegistreService {
  private apiUrl = API_URL; // URL do backend Spring Boot

  constructor(private http: HttpClient) {}

  // Função para registrar um novo usuário
  register(name: string, email: string, password: string): Observable<any> {
    const url = `${this.apiUrl}/users/register`;
    const body = { name, email, password };

    return this.http.post<any>(url, body, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    });
  }
}


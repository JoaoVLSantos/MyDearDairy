import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  constructor(private router: Router) { }

  // Função que simula o login
  onLogin() {
    // Lógica de autenticação aqui, exemplo:
    this.router.navigate(['/login']); // Redireciona para a tela principal após login
  }

  // Função que navega para a tela de cadastro
  onSignUp() {
    this.router.navigate(['/singup']); // Redireciona para a tela de cadastro
  }
}
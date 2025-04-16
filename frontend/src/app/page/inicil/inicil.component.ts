import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-inicil',
  templateUrl: './inicil.component.html',
  styleUrls: ['./inicil.component.css']
})
export class InicilComponent {

  constructor(private router: Router) { }

  // Função que simula o login
  onLogin() {
    this.router.navigate(['/mood-entry']);
  }

  // Função que navega para a tela de cadastro
  onSignUp() {
    this.router.navigate(['/buscar'])
  }
}

import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../auth/auth.sevice';  // Importe o serviço de autenticação

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  email: string = '';
  password: string = '';
  message: string = '';

  constructor(private router: Router, private authService: AuthService) {}

  // Função de login
  onLogin() {
    if (this.email && this.password) {
      this.authService.login(this.email, this.password).subscribe(
        (response) => {
          // Se o login for bem-sucedido, armazenamos o token e os dados do usuário
          this.authService.saveToken(response.token);
          this.authService.saveUser(response.user);
          this.router.navigate(['/home']);  // Redireciona para a página inicial após o login
        },
        (error) => {
          console.error('Erro no login:', error);
          this.message = 'Email ou senha inválidos!';  // Exibe mensagem de erro
        }
      );
    } else {
      this.message = 'Por favor, preencha o email e a senha!';  // Exibe mensagem caso campos estejam vazios
    }
  }

  // Navegar para a tela de cadastro
  onSignUp() {
    this.router.navigate(['/signup']);
  }

  // Navegar para a tela inicial
  onGoHome() {
    this.router.navigate(['/home']);
  }
}



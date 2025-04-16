import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { RegistreService } from '../../service/registeService'; // Importe o serviço de registro

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
})
export class SignupComponent {
  message: string = '';  // Variável para armazenar a mensagem de sucesso ou erro
  messageType: string = '';  // Variável para armazenar o tipo da mensagem (success, error, etc.)

  constructor(private router: Router, private registreService: RegistreService) {}

  // Função de cadastro
  onSignUp(name: string, email: string, password: string) {
    if (name && email && password) {
      this.registreService.register(name, email, password).subscribe(
        (response) => {
          console.log('Cadastro realizado com sucesso!');
          this.message = 'Usuário registrado com sucesso!';  // Mensagem de sucesso
          this.messageType = 'success'; // Tipo de mensagem
          setTimeout(() => {
            this.router.navigate(['/login']); // Redireciona para a tela de login após o cadastro
          }, 2000);  // Aguarda 2 segundos para mostrar a mensagem antes de redirecionar
        },
        (error) => {
          console.error('Erro no cadastro:', error);
          this.message = 'Falha no cadastro! Tente novamente.';  // Mensagem de erro
          this.messageType = 'error'; // Tipo de mensagem
        }
      );
    } else {
      this.message = 'Por favor, preencha todos os campos!';  // Mensagem se algum campo estiver vazio
      this.messageType = 'warning'; // Tipo de mensagem
    }
  }

  // Navegar para a tela de login
  onLogin() {
    this.router.navigate(['/login']);
  }

  // Navegar para a tela inicial
  onGoHome() {
    this.router.navigate(['/home']);
  }
}



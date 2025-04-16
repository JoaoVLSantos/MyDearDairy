import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-mood-entry',
  templateUrl: './mood-entry.component.html',
  styleUrls: ['./mood-entry.component.css']
})
export class MoodEntryComponent {

  mood: string = '';  // O humor escolhido
  feeling: string = '';  // O que o usuário está sentindo
  dayName: string = '';  // Nome do dia
  entryDate: string = new Date().toLocaleDateString();  // Data de hoje (por padrão)

  constructor(private router: Router) { }

  // Função para salvar a entrada
  onSaveEntry(feelingInput: string, dayNameInput: string) {
    this.feeling = feelingInput;
    this.dayName = dayNameInput;

    if (this.mood && this.feeling && this.dayName) {
      console.log('Entrada salva:', {
        mood: this.mood,
        feeling: this.feeling,
        dayName: this.dayName,
        date: this.entryDate
      });
      alert('Entrada salva com sucesso!');
      this.router.navigate(['/inicil']); // Redireciona de volta para o diário ou onde você desejar
    } else {
      alert('Por favor, preencha todos os campos!');
    }
  }
}

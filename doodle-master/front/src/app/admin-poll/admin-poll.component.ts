import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { ActivatedRoute } from '@angular/router';
import { PollService } from '../poll-service.service';
import { Poll, User, PollChoice, PollCommentElement } from '../model/model';
import { EventInput } from '@fullcalendar/core';

@Component({
  selector: 'app-admin-poll',
  templateUrl: './admin-poll.component.html',
  styleUrls: ['./admin-poll.component.css'],
  providers: [MessageService]
})
export class AdminPollComponent implements OnInit {

  constructor(
    public messageService: MessageService,
    private actRoute: ActivatedRoute,
    private pollService: PollService
  ) { }

  slugid: string;
  poll: Poll;
  events: EventInput[] = [];
  uniqueUsers: User[] = [];
  userChoices: Map<number, PollChoice[]> = new Map();
  comments: PollCommentElement[];

  ngOnInit(): void {
    this.actRoute.paramMap.subscribe(params => {
      this.slugid = params.get('slugadminid');

      this.pollService.getPollBySlugAdminId(this.slugid).subscribe(p => {
        this.poll = p;

        if (p != null) {
          this.pollService.getComentsBySlugId(this.poll.slug)
            .subscribe(cs => this.comments = cs);
        }

        this.uniqueUsers = [];

        this.poll.pollChoices.forEach(pc => {
          pc.users.forEach(user => {
            if (!this.uniqueUsers.find(us => us.id === user.id)) {
              this.uniqueUsers.push(user);
              this.userChoices.set(user.id, []);
            }
          });

          this.events.push({
            title: '',
            start: pc.startDate,
            end: pc.endDate,
            resourceEditable: false,
            eventResizableFromStart: false,
            backgroundColor: 'red',
            extendedProps: {
              choiceid: pc.id,
              selected: false
            }
          });
        });

        this.poll.pollChoices.forEach(pc => {
          pc.users.forEach(us => {
            this.userChoices.get(us.id)?.push(pc);
          });
        });

      });
    });
  }

  selectEvent(event: EventInput): void {
    this.pollService.selectEvent(event.extendedProps.choiceid).subscribe(
      () => {
        this.messageService.add({
          severity: 'success',
          summary: 'Données enregistrées',
          detail: 'Le sondage est maintenant clos'
        });
        this.poll.clos = true;
      },
      () => {
        this.messageService.add({
          severity: 'warn',
          summary: 'Erreur',
          detail: 'Impossible de clôturer le sondage'
        });
      }
    );
  }
}
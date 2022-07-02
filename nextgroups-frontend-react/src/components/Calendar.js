import React, {Component} from 'react';
import moment from 'moment';

class Calendar extends Component {

    constructor(props, context) {
        super(props, context);
        this.onTournamentChosen = props.onTournamentChosen;
    }

    state = {
        calenderResponse: {
            tournaments: []
        }
    }

    componentDidMount() {
        fetch('/api/calender')
            .then(res => res.json())
            .then((data) => {
                this.setState({calenderResponse: data})
            })
            .catch(console.log)
    }

    render() {
        return (
            <div>
                <h3 className="mb-3">Saint-Petersburg Beach Volley Tour 2022</h3>
                {this.state.calenderResponse.tournaments
                    .filter((tournament) => {
                        let arrayOfStrings = tournament.date.split('-');
                        let parsedDate = arrayOfStrings[arrayOfStrings.length - 1];
                        return new Date() <= moment(parsedDate, "DD.MM.YYYY").toDate()
                    })
                    .map((tournament) => (
                        <a href="#" className="card" key={'tournament_' + tournament.id} onClick={() => this.onTournamentChosen(tournament.url)}>
                            <div className="card-body">
                                <h5 className="card-title">{tournament.name}</h5>
                                <p className="card-text">{tournament.categories}</p>
                                <p className="card-text">{tournament.date}</p>
                            </div>
                        </a>
                    ))
                }
            </div>
        )
    }
}

export default Calendar
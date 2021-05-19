import React, {useState} from 'react';

import Calendar from "./components/Calendar";
import GroupPage from "./components/GroupsPage";

function App() {
    let [toggleCalendar, setToggleCalendar] = useState(true)
    let [tournamentUrl, setTournamentUrl] = useState('')

    const tournamentUrlBase = '/api/nextGroups?'

    return (
        <div className="App container">
            {toggleCalendar && <Calendar onTournamentChosen={url => {
                setToggleCalendar(!toggleCalendar);
                setTournamentUrl(tournamentUrlBase + 'tournament=' + url)
            }}/>}
            {!toggleCalendar && <GroupPage url={tournamentUrl} onBackClicked={() => {
                setToggleCalendar(!toggleCalendar);
            }}/>}
        </div>
    )
}

export default App;

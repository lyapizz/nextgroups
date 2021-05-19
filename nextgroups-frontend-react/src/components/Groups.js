import React from 'react'

const Groups = ({groups}) => {

    return (
        <div>
            {groups.map((group, index) => (
                <table className="table table-striped table-hover" key={'table_' + index}>
                    <thead>
                    <tr>
                        <td>
                            Group {group.id}
                        </td>
                    </tr>
                    </thead>
                    <Teams teams={group.teams}/>
                </table>
            ))}
        </div>
    )
};

const Teams = ({teams}) => {

    return (
        <tbody>
        {teams.map((team, index) => (
            <tr key={'team_' + index}>
                <td>
                    <a href={team.teamPage}> {team.player1} & {team.player2}</a>
                </td>
            </tr>
        ))}
        </tbody>
    )
};

export default Groups
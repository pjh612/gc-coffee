import React from "react";
import axios from "axios";

export function Designer(props) {
  const id = props.id;
  const name = props.name;
  const position = props.position;
  const  joinedAt = props.joinedAt;

  const handleSubmit = (e) => {
    axios({
      url:`http://localhost:8080/api/v1/designers/`+id,
      method : 'delete'
    }

    ).then(
        v => {alert("디자이너 정보가 삭제되었습니다.")
        props.onRemove(id);
        },
        e => {
          alert("서버 장애");
          console.error(e);
        })
  }
  const onClick = (e)=> {
    props.onItemClick(id);
  }

  return <>

    <div className="col">
      <div className="row text-muted" onClick={onClick}>{id}</div>
      <div className="row">{name}</div>
    </div>
    <div className="col text-center position">{position}</div>
    <div className="col text-center position">{joinedAt}</div>
    <button className="btn btn-small btn-outline-dark" onClick={handleSubmit}>삭제</button>
  </>
}
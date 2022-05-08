import {Button, FormGroup, FormLabel, Modal} from "react-bootstrap";
import axios from "axios";

export const CustomerDetail = ({customers, setCustomers, open, commentModal, setCommentModal, message, title, id}) => {
    const handleClose = () => {
        setCommentModal({open: false});
        setCustomers(
            customers.map(customer => customer.id === commentModal.id ? {...customer, comment: message} : customer)
        )

        axios({
                url: `http://localhost:8080/api/v1/customers/`,
                method: 'patch',
                data: {
                    id: id,
                    comment: message
                }
            }
        ).then(
            res => {
                alert("코멘트가 수정되었습니다.")
            },
            e => {
                alert("서버 장애");
                console.error(e);
            })
    }

    const onHandleTextareaChanged = (e) => {
        setCommentModal({...commentModal, message: e.target.value})
    }

    return (<>

        <Modal show={open} onHide={() => {
            setCommentModal({open: false})
        }}>
            <Modal.Header closeButton>
                <Modal.Title>{title}</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <FormGroup>
                    <FormLabel for="exampleText">
                        Comment
                    </FormLabel>
                    <textarea
                        className="form-control"
                        id="exampleFormControlTextarea1"
                        rows="5"
                        value={message} onChange={onHandleTextareaChanged}></textarea>
                </FormGroup>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="primary" onClick={handleClose}>
                    OK
                </Button>
            </Modal.Footer>
        </Modal>

    </>)

}
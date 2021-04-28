<?php

namespace App\Controller;

use App\Entity\Playlist;
use App\Form\PlaylistType;
use App\Repository\CalendarRepository;
use App\Repository\PlaylistRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Dompdf\Dompdf;
use Dompdf\Options;


class PlaylistController extends AbstractController
{
    /**
     * @Route("/", name="playlist")
     */
    public function index(CalendarRepository $calendar): Response
    {
        $events = $calendar->findAll();
        $rdvs =[];
        foreach ($events as $event){
            $rdvs[] = [
                'id' => $event->getId(),
                'start' => $event->getStart()->format('Y-m-d H:i:s'),
                'end' => $event->getEnd()->format('Y-m-d H:i:s'),
                'title' => $event->getTitle(),
                'description' => $event->getDescription(),
                'backgroundColor' => $event->getBackgroundColor(),
                'borderColor' => $event->getBorderColor(),
                'textColor' => $event->getTextColor(),
                'allDay' => $event->getAllDay(),
            ];
        }
        $data = json_encode($rdvs);
        return $this->render('playlist/index.html.twig', compact('data'));
    }

    /**
     * @param PlaylistRepository $repository
     * @return Response
     * @Route("/Affiche",name="AffichePlaylist")
     */
    public function Affiche(PlaylistRepository $repository){
       //$repp=$this->getDoctrine()->getRepository(Playlist::class);
       $playlist=$repository->findAll();
       return $this->render('playlist/Affiche.html.twig',
       ['playlist'=>$playlist]);
    }
    /**
     * @Route("/Supp/{id}", name="d")
     */
    function Delete($id, PlaylistRepository $repository){
        $playlist=$repository->find($id);
        $em=$this->getDoctrine()->getManager();
        $em->remove($playlist);
        $em->flush();
        return $this->redirectToRoute('AffichePlaylist');
    }

    /**
     * @param Request $request
     * @return Response
     * @Route("playlist/Add",name="Add")
     */
    function Add(Request $request){
        $playlist=new Playlist();
        $form=$this->createForm(PlaylistType::class,$playlist);
        $form->add('Ajouter',SubmitType::class);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            $em=$this->getDoctrine()->getManager();
            $em->persist($playlist);
            $em->flush();
            return $this->redirectToRoute('AffichePlaylist');
        }
        return $this->render('playlist/Add.html.twig',
        ['form'=>$form->createView()]);
    }
    /**
     * @Route("playlist/Update/{id}",name="update")
     */
    function Update(PlaylistRepository $repository,$id,Request $request){
        $playlist=$repository->find($id);
        $form=$this->createForm(PlaylistType::class,$playlist);
        $form->add('Update',SubmitType::class);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            $em=$this->getDoctrine()->getManager();
            $em->flush();
            return $this->redirectToRoute('AffichePlaylist');
        }
        return $this->render('playlist/Update.html.twig',[
            'formUpdated'=>$form->createView()
        ]);

    }

    /**
     * @return Response
     * @Route("/generatePDF", name="generatePDF")
     */
    public function generatePDF(PlaylistRepository $repository): Response
    {
        $em=$this->getDoctrine()->getManager();

        $playlist=$repository->findAll();


        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');
        $dompdf = new Dompdf($pdfOptions);

        $html = $this->renderView('playlist/Affiche.html.twig', [
            'playlist' => $playlist,
        ]);

        // Load HTML to Dompdf
        $dompdf->loadHtml($html);

        // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
        $dompdf->setPaper('A4', 'landscape');

        // Render the HTML as PDF
        $dompdf->render();

        // Output the generated PDF to Browser (force download)
        $dompdf->stream("Liste des Playlist", ["Attachment" => true]);


        return $this->render('playlist/Affiche.html.twig', [
            'playlist' => $playlist,
        ]);


    }


}

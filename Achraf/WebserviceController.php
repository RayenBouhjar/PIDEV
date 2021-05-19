<?php

namespace App\Controller;

use App\Entity\Playlist;
use App\Repository\PistRepository;
use App\Repository\PlaylistRepository;
use DateTime;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\SerializerInterface;

/**
 * Class WebserviceController
 * @package App\Controller
 * @Route("/playlists", name="web_")
 */
class WebserviceController extends AbstractController
{
    /**
     * @Route("/liste", name="liste", methods={"GET"})
     */
    public function liste(PlaylistRepository $playlistRepo)
    {
       // On recupére la liste des playlists
        $playlist = $playlistRepo->apiFindAll();
        // On va specifier qu'on utilise un encoder en Json
        $encoders = [new JsonEncoder()];

        //On instancie un normaliseur pour convertir la collection en tableau

        $normalizers = [new ObjectNormalizer()];

        // On fait la convertie en Json
        //On instancie le convertisseur
        $serializer = new Serializer($normalizers, $encoders);

        //On convertie

        $jsonContent = $serializer->serialize($playlist, 'json', [
            'circular_reference_handler' => function($object){
                return $object->getId();
            }
        ]);
        // On instancie la reponse de symfony
        $response = new Response($jsonContent);
        // On ajoute l'entete HTTP

        $response->headers->set('Content-Type', 'application/json');

        //On envoie la réponse
        return $response;

    }


    /**
     * @param Request $request
     * @param SerializerInterface $serializer
     * @param $nom
     * @param $date
     * @return Response
     * @Route ("/playlistsadd/{nom}/{date}",name="test", methods={"GET"})
     */
    public function addPlaylistsJSON(Request $request, SerializerInterface $serializer,$nom, $date){
//        console.log("nom:"+$nom);
        $playlist = new Playlist();
        $em = $this->getDoctrine()->getManager();
        $playlist->setNom($nom);
        $dateTime = DateTime::createFromFormat('Y-m-d', $date);
        $playlist->setDate($dateTime);

        $content = $request->getContent();
        // $data = $serializer->deserialize($content, Maison::class, 'json');
        $em->persist($playlist);
        $em->flush();
        return new Response('playlist added successfully');
    }

    /**
     * @Route("/listepist/{id}", name="listepist", methods={"GET"})
     */
    public function listePist(PistRepository $pistRepository,$id)
    {
        // On recupére la liste des playlists
        $pist = $pistRepository->findById($id);
        // On va specifier qu'on utilise un encoder en Json
        $encoders = [new JsonEncoder()];

        //On instancie un normaliseur pour convertir la collection en tableau

        $normalizers = [new ObjectNormalizer()];

        // On fait la convertie en Json
        //On instancie le convertisseur
        $serializer = new Serializer($normalizers, $encoders);

        //On convertie

        $jsonContent = $serializer->serialize($pist, 'json', [
            'circular_reference_handler' => function($object){
                return $object->getId();
            }
        ]);
        // On instancie la reponse de symfony
        $response = new Response($jsonContent);
        // On ajoute l'entete HTTP

        $response->headers->set('Content-Type', 'application/json');

        //On envoie la réponse
        return $response;

    }
}
